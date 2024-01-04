
#include <jni.h>
void *tree_sitter_markdown();
/*
 * Class:     org_treesitter_TreeSitterMarkdown
 * Method:    tree_sitter_markdown
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterMarkdown_tree_1sitter_1markdown
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_markdown();
}
