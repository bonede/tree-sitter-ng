
#include <jni.h>
void *tree_sitter_ssh_client_config();
/*
 * Class:     org_treesitter_TreeSitterSshClientConfig
 * Method:    tree_sitter_ssh_client_config
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterSshClientConfig_tree_1sitter_1ssh_1client_1config
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_ssh_client_config();
}
