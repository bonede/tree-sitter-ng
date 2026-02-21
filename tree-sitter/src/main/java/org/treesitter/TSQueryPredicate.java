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

    protected List<TSNode> findNodes(TSQueryMatch match, String captureName, TSQuery query) {
        TSQueryCapture[] captures = match.getCaptures();
        if (captures == null) return Collections.emptyList();
        List<TSNode> nodes = new ArrayList<>();
        for (TSQueryCapture capture : captures) {
            if (captureName.equals(query.getCaptureNameForId(capture.getIndex()))) {
                nodes.add(capture.getNode());
            }
        }
        return nodes;
    }

    /**
     * Handles {@code #eq?}, {@code #not-eq?}, {@code #any-eq?}, {@code #any-not-eq?}
     */
    public static final class TSQueryPredicateEq extends TSQueryPredicate {
        private final String capture;
        private final String value;
        private final boolean isPositive;
        private final boolean isAny;
        private final boolean isCapture;
        private final TSQuery query;

        public static final Set<String> NAMES = Set.of("eq?", "not-eq?", "any-eq?", "any-not-eq?");

        public TSQueryPredicateEq(String name, String capture, String value, boolean isCapture, TSQuery query) {
            super(name);
            this.capture = capture;
            this.value = value;
            this.isPositive = !name.contains("not-");
            this.isAny = name.startsWith("any-");
            this.isCapture = isCapture;
            this.query = query;
        }

        @Override
        public boolean test(TSQueryMatch match, Function<TSNode, String> textProvider) {
            return isCapture ? testCapture(match, textProvider) : testLiteral(match, textProvider);
        }

        private boolean testCapture(TSQueryMatch match, Function<TSNode, String> textProvider) {
            java.util.stream.Stream<TSNode> findNodes1 = findNodes(match, capture, query).stream();
            List<TSNode> findNodes2 = findNodes(match, value, query);
            java.util.function.Predicate<TSNode> predicate = n1 -> {
                String text1 = textProvider.apply(n1);
                return findNodes2.stream().anyMatch(n2 ->
                        Objects.equals(text1, textProvider.apply(n2)) == isPositive);
            };
            return isAny ? findNodes1.anyMatch(predicate) : findNodes1.allMatch(predicate);
        }

        private boolean testLiteral(TSQueryMatch match, Function<TSNode, String> textProvider) {
            List<TSNode> nodes = findNodes(match, capture, query);
            if (nodes.isEmpty()) return !isPositive;
            java.util.function.Predicate<TSNode> predicate = node -> {
                String text = textProvider.apply(node);
                return Objects.equals(text, value) == isPositive;
            };
            return isAny ? nodes.stream().anyMatch(predicate) : nodes.stream().allMatch(predicate);
        }
    }

    /**
     * Handles {@code #match?}, {@code #not-match?}, {@code #any-match?}, {@code #any-not-match?}
     */
    public static final class TSQueryPredicateMatch extends TSQueryPredicate {
        private final String capture;
        private final Pattern pattern;
        private final boolean isPositive;
        private final boolean isAny;
        private final TSQuery query;

        public static final Set<String> NAMES = Set.of("match?", "not-match?", "any-match?", "any-not-match?");

        public TSQueryPredicateMatch(String name, String capture, String patternStr, TSQuery query) {
            super(name);
            this.capture = capture;
            this.pattern = Pattern.compile(patternStr);
            this.isPositive = !name.contains("not-");
            this.isAny = name.startsWith("any-");
            this.query = query;
        }

        @Override
        public boolean test(TSQueryMatch match, Function<TSNode, String> textProvider) {
            List<TSNode> nodes = findNodes(match, capture, query);
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
        private final String capture;
        private final Set<String> values;
        private final boolean isPositive;
        private final TSQuery query;

        public static final Set<String> NAMES = Set.of("any-of?", "not-any-of?");

        public TSQueryPredicateAnyOf(String name, String capture, List<String> values, TSQuery query) {
            super(name);
            this.capture = capture;
            this.values = new HashSet<>(values);
            this.isPositive = name.equals("any-of?");
            this.query = query;
        }

        @Override
        public boolean test(TSQueryMatch match, Function<TSNode, String> textProvider) {
            return findNodes(match, capture, query).stream().noneMatch(node -> {
                String text = textProvider.apply(node);
                return (text != null && values.contains(text)) != isPositive;
            });
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
