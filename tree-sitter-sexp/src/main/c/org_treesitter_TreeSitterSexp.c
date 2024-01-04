
#include <jni.h>
void *tree_sitter_sexp();
/*
 * Class:     org_treesitter_TreeSitterSexp
 * Method:    tree_sitter_sexp
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterSexp_tree_1sitter_1sexp
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_sexp();
}
