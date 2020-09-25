package com.xujiajie.wanandroid

import com.xujiajie.wanandroid.base.BaseBean
import java.io.Serializable

/**
 * 创建日期 2020/9/25
 * 描述：
 */
data class HomeFragment1Bean(
    var apkLink: String,
    var audit: Int,
    var author: String,
    var canEdit: Boolean,
    var chapterId: Int,
    var chapterName: String,
    var collect: Boolean,
    var courseId: Int,
    var desc: String,
    var descMd: String,
    var envelopePic: String,
    var fresh: Boolean,
    var id: Int,
    var link: String,
    var niceDate: String,
    var niceShareDate: String,
    var origin: String,
    var prefix: String,
    var projectLink: String,
    var publishTime: Long,
    var realSuperChapterId: Int,
    var selfVisible: Int,
    var shareDate: Long,
    var shareUser: String,
    var superChapterId: Int,
    val superChapterName: String,
    var tags: List<Any>,
    var title: String,
    var type: Int,
    var userId: Int,
    var visible: Int,
    var zan: Int
) :Serializable{
    override fun toString(): String {
        return "HomeFragment1Bean(apkLink='$apkLink', audit=$audit, author='$author', canEdit=$canEdit, chapterId=$chapterId, chapterName='$chapterName', collect=$collect, courseId=$courseId, desc='$desc', descMd='$descMd', envelopePic='$envelopePic', fresh=$fresh, id=$id, link='$link', niceDate='$niceDate', niceShareDate='$niceShareDate', origin='$origin', prefix='$prefix', projectLink='$projectLink', publishTime=$publishTime, realSuperChapterId=$realSuperChapterId, selfVisible=$selfVisible, shareDate=$shareDate, shareUser='$shareUser', superChapterId=$superChapterId, superChapterName='$superChapterName', tags=$tags, title='$title', type=$type, userId=$userId, visible=$visible, zan=$zan)"
    }
}