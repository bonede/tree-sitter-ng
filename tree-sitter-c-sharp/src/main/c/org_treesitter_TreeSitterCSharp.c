
#include <jni.h>
void *tree_sitter_c_sharp();
/*
 * Class:     org_treesitter_TreeSitterCSharp
 * Method:    tree_sitter_c_sharp
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterCSharp_tree_1sitter_1c_1sharp
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_c_sharp();
}
