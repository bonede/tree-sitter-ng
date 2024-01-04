
#include <jni.h>
void *tree_sitter_toml();
/*
 * Class:     org_treesitter_TreeSitterToml
 * Method:    tree_sitter_toml
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterToml_tree_1sitter_1toml
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_toml();
}
