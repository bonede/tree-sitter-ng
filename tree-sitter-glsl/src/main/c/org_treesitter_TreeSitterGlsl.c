
#include <jni.h>
void *tree_sitter_glsl();
/*
 * Class:     org_treesitter_TreeSitterGlsl
 * Method:    tree_sitter_glsl
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterGlsl_tree_1sitter_1glsl
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_glsl();
}
