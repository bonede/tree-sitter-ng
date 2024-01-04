
#include <jni.h>
void *tree_sitter_dart();
/*
 * Class:     org_treesitter_TreeSitterDart
 * Method:    tree_sitter_dart
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterDart_tree_1sitter_1dart
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_dart();
}
