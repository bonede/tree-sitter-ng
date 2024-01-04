
#include <jni.h>
void *tree_sitter_commonlisp();
/*
 * Class:     org_treesitter_TreeSitterCommonlisp
 * Method:    tree_sitter_commonlisp
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterCommonlisp_tree_1sitter_1commonlisp
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_commonlisp();
}
