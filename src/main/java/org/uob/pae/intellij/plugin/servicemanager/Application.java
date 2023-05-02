package org.uob.pae.intellij.plugin.servicemanager;

import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;
import org.uob.pae.intellij.plugin.servicemanager.ui.MainPanel;

/**
 * @author Dushmantha
 */
public class Application implements ToolWindowFactory, DumbAware {

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        Content content = ContentFactory.SERVICE.getInstance().createContent(
                MainPanel.getInstance().getMainPanel(),
                "Services Manager",
                false);
        toolWindow.getContentManager().addContent(content);
    }

}
