
#include <jni.h>
void *tree_sitter_scala();
/*
 * Class:     org_treesitter_TreeSitterScala
 * Method:    tree_sitter_scala
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterScala_tree_1sitter_1scala
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_scala();
}
