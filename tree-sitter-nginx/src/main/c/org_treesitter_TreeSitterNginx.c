
#include <jni.h>
void *tree_sitter_nginx();
/*
 * Class:     org_treesitter_TreeSitterNginx
 * Method:    tree_sitter_nginx
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterNginx_tree_1sitter_1nginx
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_nginx();
}
