
#include <jni.h>
void *tree_sitter_yaml();
/*
 * Class:     org_treesitter_TreeSitterYaml
 * Method:    tree_sitter_yaml
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterYaml_tree_1sitter_1yaml
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_yaml();
}
