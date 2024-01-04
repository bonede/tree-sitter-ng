
#include <jni.h>
void *tree_sitter_cuda();
/*
 * Class:     org_treesitter_TreeSitterCuda
 * Method:    tree_sitter_cuda
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterCuda_tree_1sitter_1cuda
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_cuda();
}
