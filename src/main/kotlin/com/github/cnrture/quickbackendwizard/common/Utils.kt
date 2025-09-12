package com.github.cnrture.quickbackendwizard.common

import com.intellij.ide.BrowserUtil
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.util.IconLoader

object Utils {
    fun showInfo(title: String? = null, message: String, type: NotificationType = NotificationType.INFORMATION) {
        val notification = NotificationGroupManager.getInstance()
            .getNotificationGroup("QuickBackendWizard")
            .createNotification(
                title = title ?: "Quick Backend Wizard",
                content = message,
                type = type,
            )
        notification.addAction(
            object : AnAction("Contact Developer") {
                override fun actionPerformed(e: AnActionEvent) {
                    BrowserUtil.browse("https://candroid.dev/")
                }
            }
        )
        notification.addAction(
            object : AnAction("Open Plugin Page") {
                override fun actionPerformed(e: AnActionEvent) {
                    BrowserUtil.browse("https://quickprojectwizard.candroid.dev/overview")
                }
            }
        )
        notification.icon = IconLoader.getIcon("/icons/pluginIcon.svg", this::class.java)
        notification.notify(null)
    }
}