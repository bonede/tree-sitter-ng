#include <jni.h>
void *tree_sitter_sosl();
/*
 * Class:     org_treesitter_TreeSitterSosl
 * Method:    tree_sitter_sosl
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterSosl_tree_1sitter_1sosl
  (JNIEnv *env, jclass clz){
  return (jlong) tree_sitter_sosl();
}

