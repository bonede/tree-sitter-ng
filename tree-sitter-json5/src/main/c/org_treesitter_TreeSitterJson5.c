
#include <jni.h>
void *tree_sitter_json5();
/*
 * Class:     org_treesitter_TreeSitterJson5
 * Method:    tree_sitter_json5
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterJson5_tree_1sitter_1json5
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_json5();
}
