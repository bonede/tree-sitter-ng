#include <jni.h>
void *tree_sitter_c();
/*
 * Class:     org_treesitter_TreeSitterC
 * Method:    tree_sitter_c
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterC_tree_1sitter_1c
  (JNIEnv *env, jclass clz){
    return (jlong) tree_sitter_c();
}
