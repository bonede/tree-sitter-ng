
#include <jni.h>
void *tree_sitter_smali();
/*
 * Class:     org_treesitter_TreeSitterSmali
 * Method:    tree_sitter_smali
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterSmali_tree_1sitter_1smali
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_smali();
}
