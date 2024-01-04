
#include <jni.h>
void *tree_sitter_perl();
/*
 * Class:     org_treesitter_TreeSitterPerl
 * Method:    tree_sitter_perl
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterPerl_tree_1sitter_1perl
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_perl();
}
