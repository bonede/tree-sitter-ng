
#include <jni.h>
void *tree_sitter_fish();
/*
 * Class:     org_treesitter_TreeSitterFish
 * Method:    tree_sitter_fish
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterFish_tree_1sitter_1fish
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_fish();
}
