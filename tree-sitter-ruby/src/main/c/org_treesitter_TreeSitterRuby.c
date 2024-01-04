
#include <jni.h>
void *tree_sitter_ruby();
/*
 * Class:     org_treesitter_TreeSitterRuby
 * Method:    tree_sitter_ruby
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterRuby_tree_1sitter_1ruby
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_ruby();
}
