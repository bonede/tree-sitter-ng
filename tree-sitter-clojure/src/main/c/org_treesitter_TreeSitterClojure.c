
#include <jni.h>
void *tree_sitter_clojure();
/*
 * Class:     org_treesitter_TreeSitterClojure
 * Method:    tree_sitter_clojure
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterClojure_tree_1sitter_1clojure
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_clojure();
}
