
#include <jni.h>
void *tree_sitter_pod();
/*
 * Class:     org_treesitter_TreeSitterPod
 * Method:    tree_sitter_pod
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterPod_tree_1sitter_1pod
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_pod();
}
