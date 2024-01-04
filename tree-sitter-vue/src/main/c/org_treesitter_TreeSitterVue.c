
#include <jni.h>
void *tree_sitter_vue();
/*
 * Class:     org_treesitter_TreeSitterVue
 * Method:    tree_sitter_vue
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterVue_tree_1sitter_1vue
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_vue();
}
