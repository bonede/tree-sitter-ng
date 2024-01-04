
#include <jni.h>
void *tree_sitter_cmake();
/*
 * Class:     org_treesitter_TreeSitterCmake
 * Method:    tree_sitter_cmake
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterCmake_tree_1sitter_1cmake
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_cmake();
}
