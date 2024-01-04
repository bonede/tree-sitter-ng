
#include <jni.h>
void *tree_sitter_elisp();
/*
 * Class:     org_treesitter_TreeSitterElisp
 * Method:    tree_sitter_elisp
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterElisp_tree_1sitter_1elisp
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_elisp();
}
