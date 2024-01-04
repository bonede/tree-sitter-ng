
#include <jni.h>
void *tree_sitter_dockerfile();
/*
 * Class:     org_treesitter_TreeSitterDockerfile
 * Method:    tree_sitter_dockerfile
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterDockerfile_tree_1sitter_1dockerfile
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_dockerfile();
}
