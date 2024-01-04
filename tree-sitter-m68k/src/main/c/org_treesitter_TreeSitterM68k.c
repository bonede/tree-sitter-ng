
#include <jni.h>
void *tree_sitter_m68k();
/*
 * Class:     org_treesitter_TreeSitterM68k
 * Method:    tree_sitter_m68k
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterM68k_tree_1sitter_1m68k
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_m68k();
}
