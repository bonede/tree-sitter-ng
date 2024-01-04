
#include <jni.h>
void *tree_sitter_yang();
/*
 * Class:     org_treesitter_TreeSitterYang
 * Method:    tree_sitter_yang
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterYang_tree_1sitter_1yang
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_yang();
}
