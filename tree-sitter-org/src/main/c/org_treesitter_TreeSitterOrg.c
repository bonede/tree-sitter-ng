
#include <jni.h>
void *tree_sitter_org();
/*
 * Class:     org_treesitter_TreeSitterOrg
 * Method:    tree_sitter_org
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterOrg_tree_1sitter_1org
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_org();
}
