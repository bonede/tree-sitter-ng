
#include <jni.h>
void *tree_sitter_rasi();
/*
 * Class:     org_treesitter_TreeSitterRasi
 * Method:    tree_sitter_rasi
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterRasi_tree_1sitter_1rasi
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_rasi();
}
