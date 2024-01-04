
#include <jni.h>
void *tree_sitter_hcl();
/*
 * Class:     org_treesitter_TreeSitterHcl
 * Method:    tree_sitter_hcl
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterHcl_tree_1sitter_1hcl
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_hcl();
}
