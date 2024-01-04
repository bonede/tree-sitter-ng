
#include <jni.h>
void *tree_sitter_twig();
/*
 * Class:     org_treesitter_TreeSitterTwig
 * Method:    tree_sitter_twig
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterTwig_tree_1sitter_1twig
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_twig();
}
