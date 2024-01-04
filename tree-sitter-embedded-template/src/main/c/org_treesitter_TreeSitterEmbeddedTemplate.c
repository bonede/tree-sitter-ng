
#include <jni.h>
void *tree_sitter_embedded_template();
/*
 * Class:     org_treesitter_TreeSitterEmbeddedTemplate
 * Method:    tree_sitter_embedded_template
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterEmbeddedTemplate_tree_1sitter_1embedded_1template
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_embedded_template();
}
