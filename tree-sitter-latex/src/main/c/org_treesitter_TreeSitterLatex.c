
#include <jni.h>
void *tree_sitter_latex();
/*
 * Class:     org_treesitter_TreeSitterLatex
 * Method:    tree_sitter_latex
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterLatex_tree_1sitter_1latex
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_latex();
}
