package org.treesitter;

import java.util.*;
import java.util.function.Function;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * A query predicate that associates conditions with a pattern.
 */
public abstract class TSQueryPredicate {
    private final String name;

    protected TSQueryPredicate(String name) {
        this.name = name;
    }

    /**
     * Get the name of the predicate.
     */
    public String getName() {
        return name;
    }

    /**
     * Test the predicate against a match.
     *
     * @param match        The query match.
     * @param textProvider A function that provides the text for a given node.
     * @return true if the predicate is satisfied.
     */
    public abstract boolean test(TSQueryMatch match, Function<TSNode, String> textProvider);

    /**
     * Test the predicate against a match using raw source bytes to avoid allocations.
     *
     * @param match       The query match.
     * @param sourceBytes The source bytes (UTF-8).
     * @return true if the predicate is satisfied.
     */
    public boolean test(TSQueryMatch match, byte[] sourceBytes) {
        return test(match, n -> {
            if (n == null || n.isNull() || sourceBytes == null) return "";
            int start = n.getStartByte();
            int end = n.getEndByte();
            if (start < 0 || start > end || start >= sourceBytes.length) {
                return "";
            }
            int length = Math.min(end, sourceBytes.length) - start;
            return new String(sourceBytes, start, length, java.nio.charset.StandardCharsets.UTF_8);
        });
    }

    protected static boolean arrayEquals(byte[] a, int aOffset, int aLength, byte[] b, int bOffset, int bLength) {
        if (aLength != bLength) return false;
        for (int i = 0; i < aLength; i++) {
            if (a[aOffset + i] != b[bOffset + i]) return false;
        }
        return true;
    }

    protected List<TSNode> findNodes(TSQueryMatch match, int captureId) {
        TSQueryCapture[] captures = match.getCaptures();
        if (captures == null) return Collections.emptyList();
        List<TSNode> nodes = new ArrayList<>();
        for (TSQueryCapture capture : captures) {
            // In tree-sitter, the capture index is the ID within the query.
            if (capture.getIndex() == captureId) {
                nodes.add(capture.getNode());
            }
        }
        nodes.removeIf(Objects::isNull);
        return nodes;
    }

    /**
     * Handles {@code #eq?}, {@code #not-eq?}, {@code #any-eq?}, {@code #any-not-eq?}
     */
    public static final class TSQueryPredicateEq extends TSQueryPredicate {
        private final int captureId;
        private final String literalValue;
        private final byte[] literalBytes;
        private final int valueId;
        private final boolean isPositive;
        private final boolean isAny;
        private final boolean isCapture;

        public static final Set<String> NAMES = Set.of("eq?", "not-eq?", "any-eq?", "any-not-eq?");

        public TSQueryPredicateEq(String name, int captureId, String literalValue, int valueId, boolean isCapture) {
            super(name);
            this.captureId = captureId;
            this.literalValue = literalValue;
            this.literalBytes = literalValue == null ? null : literalValue.getBytes(java.nio.charset.StandardCharsets.UTF_8);
            this.valueId = valueId;
            this.isPositive = !name.contains("not-");
            this.isAny = name.startsWith("any-");
            this.isCapture = isCapture;
        }

        @Override
        public boolean test(TSQueryMatch match, Function<TSNode, String> textProvider) {
            return isCapture ? testCapture(match, textProvider) : testLiteral(match, textProvider);
        }

        @Override
        public boolean test(TSQueryMatch match, byte[] sourceBytes) {
            if (sourceBytes == null) return super.test(match, sourceBytes);
            return isCapture ? testCapture(match, sourceBytes) : testLiteral(match, sourceBytes);
        }

        private boolean testCapture(TSQueryMatch match, Function<TSNode, String> textProvider) {
            List<TSNode> nodes1 = findNodes(match, captureId);
            List<TSNode> nodes2 = findNodes(match, valueId);
            if (nodes1.isEmpty() || nodes2.isEmpty()) return !isPositive;

            java.util.function.Predicate<TSNode> predicate = n1 -> {
                String text1 = textProvider.apply(n1);
                return nodes2.stream().anyMatch(n2 ->
                        Objects.equals(text1, textProvider.apply(n2))) == isPositive;
            };
            return isAny ? nodes1.stream().anyMatch(predicate) : nodes1.stream().allMatch(predicate);
        }

        private boolean testCapture(TSQueryMatch match, byte[] sourceBytes) {
            List<TSNode> nodes1 = findNodes(match, captureId);
            List<TSNode> nodes2 = findNodes(match, valueId);
            if (nodes1.isEmpty() || nodes2.isEmpty()) return !isPositive;

            java.util.function.Predicate<TSNode> predicate = n1 -> {
                int s1 = n1.getStartByte();
                int l1 = n1.getEndByte() - s1;
                return nodes2.stream().anyMatch(n2 -> {
                    int s2 = n2.getStartByte();
                    int l2 = n2.getEndByte() - s2;
                    return arrayEquals(sourceBytes, s1, l1, sourceBytes, s2, l2);
                }) == isPositive;
            };
            return isAny ? nodes1.stream().anyMatch(predicate) : nodes1.stream().allMatch(predicate);
        }

        private boolean testLiteral(TSQueryMatch match, Function<TSNode, String> textProvider) {
            List<TSNode> nodes = findNodes(match, captureId);
            if (nodes.isEmpty()) return !isPositive;
            java.util.function.Predicate<TSNode> predicate = node -> {
                String text = textProvider.apply(node);
                return Objects.equals(text, literalValue) == isPositive;
            };
            return isAny ? nodes.stream().anyMatch(predicate) : nodes.stream().allMatch(predicate);
        }

        private boolean testLiteral(TSQueryMatch match, byte[] sourceBytes) {
            List<TSNode> nodes = findNodes(match, captureId);
            if (nodes.isEmpty()) return !isPositive;
            java.util.function.Predicate<TSNode> predicate = node -> {
                int start = node.getStartByte();
                int length = node.getEndByte() - start;
                return arrayEquals(sourceBytes, start, length, literalBytes, 0, literalBytes.length) == isPositive;
            };
            return isAny ? nodes.stream().anyMatch(predicate) : nodes.stream().allMatch(predicate);
        }
    }

    /**
     * Handles {@code #match?}, {@code #not-match?}, {@code #any-match?}, {@code #any-not-match?}
     */
    public static final class TSQueryPredicateMatch extends TSQueryPredicate {
        private final int captureId;
        private final Pattern pattern;
        private final boolean isPositive;
        private final boolean isAny;

        public static final Set<String> NAMES = Set.of("match?", "not-match?", "any-match?", "any-not-match?");

        public TSQueryPredicateMatch(String name, int captureId, String patternStr) {
            super(name);
            this.captureId = captureId;
            this.pattern = Pattern.compile(patternStr);
            this.isPositive = !name.contains("not-");
            this.isAny = name.startsWith("any-");
        }

        @Override
        public boolean test(TSQueryMatch match, Function<TSNode, String> textProvider) {
            List<TSNode> nodes = findNodes(match, captureId);
            if (nodes.isEmpty()) return !isPositive;
            java.util.function.Predicate<TSNode> predicate = n -> {
                String text = textProvider.apply(n);
                return text != null && pattern.matcher(text).find() == isPositive;
            };
            return isAny ? nodes.stream().anyMatch(predicate) : nodes.stream().allMatch(predicate);
        }
    }

    /**
     * Handles {@code #any-of?}, {@code #not-any-of?}
     */
    public static final class TSQueryPredicateAnyOf extends TSQueryPredicate {
        private final int captureId;
        private final Set<String> values;
        private final List<byte[]> valueBytes;
        private final boolean isPositive;

        public static final Set<String> NAMES = Set.of("any-of?", "not-any-of?");

        public TSQueryPredicateAnyOf(String name, int captureId, List<String> values) {
            super(name);
            this.captureId = captureId;
            this.values = new HashSet<>(values);
            this.valueBytes = values.stream()
                    .map(s -> s.getBytes(java.nio.charset.StandardCharsets.UTF_8))
                    .collect(Collectors.toList());
            this.isPositive = name.equals("any-of?");
        }

        @Override
        public boolean test(TSQueryMatch match, Function<TSNode, String> textProvider) {
            List<TSNode> nodes = findNodes(match, captureId);
            if (nodes.isEmpty()) return !isPositive;
            java.util.function.Predicate<TSNode> predicate = node -> {
                String text = textProvider.apply(node);
                return (text != null && values.contains(text)) == isPositive;
            };
            // #any-of? is typically treated as a filter where all captured nodes must satisfy it
            return nodes.stream().allMatch(predicate);
        }

        @Override
        public boolean test(TSQueryMatch match, byte[] sourceBytes) {
            if (sourceBytes == null) return super.test(match, sourceBytes);
            List<TSNode> nodes = findNodes(match, captureId);
            if (nodes.isEmpty()) return !isPositive;
            java.util.function.Predicate<TSNode> predicate = node -> {
                int start = node.getStartByte();
                int length = node.getEndByte() - start;
                return valueBytes.stream().anyMatch(val ->
                        arrayEquals(sourceBytes, start, length, val, 0, val.length)) == isPositive;
            };
            return nodes.stream().allMatch(predicate);
        }
    }

    /**
     * Handles {@code #set!}
     */
    public static final class TSQueryPredicateSet extends TSQueryPredicate {
        private final String key;
        private final String value;

        public static final Set<String> NAMES = Set.of("set!");

        public TSQueryPredicateSet(String name, String key, String value) {
            super(name);
            this.key = key;
            this.value = value;
        }

        @Override
        public boolean test(TSQueryMatch match, Function<TSNode, String> textProvider) {
            match.getMetadata().put(key, value);
            return true;
        }
    }

    /**
     * Handles {@code #is?}
     */
    public static final class TSQueryPredicateIs extends TSQueryPredicate {
        private final String key;
        private final String value;

        public static final Set<String> NAMES = Set.of("is?");

        public TSQueryPredicateIs(String name, String key, String value) {
            super(name);
            this.key = key;
            this.value = value;
        }

        @Override
        public boolean test(TSQueryMatch match, Function<TSNode, String> textProvider) {
            return Objects.equals(match.getMetadata().get(key), value);
        }
    }

    /**
     * Handles unknown predicates or directives.
     */
    public static final class TSQueryPredicateGeneric extends TSQueryPredicate {
        public TSQueryPredicateGeneric(String name) {
            super(name);
        }

        @Override
        public boolean test(TSQueryMatch match, Function<TSNode, String> textProvider) {
            return true;
        }
    }
}
