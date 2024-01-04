
#include <jni.h>
void *tree_sitter_comment();
/*
 * Class:     org_treesitter_TreeSitterComment
 * Method:    tree_sitter_comment
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterComment_tree_1sitter_1comment
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_comment();
}
