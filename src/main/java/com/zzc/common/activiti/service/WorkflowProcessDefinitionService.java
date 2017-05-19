package com.zzc.common.activiti.service;

import com.zzc.common.activiti.util.WorkflowUtils;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

/**
 * 工作流中流程以及流程实例相关Service
 * <p/>
 * Created by wufan on 2015/11/2.
 */
//@Service
public class WorkflowProcessDefinitionService {

    private static Logger logger = LoggerFactory.getLogger(WorkflowProcessDefinitionService.class);

    @Autowired
    protected RuntimeService runtimeService;

    @Autowired
    protected RepositoryService repositoryService;

    @Autowired
    protected HistoryService historyService;

    /**
     * 根据流程实例ID查询流程定义对象
     *
     * @param processInstanceId 流程实例ID
     * @return 流程定义对象{@link ProcessDefinition}
     */
    public ProcessDefinition findProcessDefinitionByPid(String processInstanceId) {
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        String processDefinitionId = historicProcessInstance.getProcessDefinitionId();
        ProcessDefinition processDefinition = findProcessDefinition(processDefinitionId);
        return processDefinition;
    }

    /**
     * 根据流程定义ID查询流程定义对象
     *
     * @param processDefinitionId 流程定义对象ID
     * @return 流程定义对象{@link ProcessDefinition}
     */
    public ProcessDefinition findProcessDefinition(String processDefinitionId) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
        return processDefinition;
    }

    /**
     * 部署classpath下面的流程定义
     *
     * @param processKey 流程定义KEY
     * @throws Exception
     */
    public void deployFromClasspath(String exportDir, String... processKey) throws Exception {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        String[] processKeys = {"leave", "leave-dynamic-from", "leave-formkey", "dispatch"};
        for (String loopProcessKey : processKeys) {
            // 需要过滤指定流程
            if (ArrayUtils.isNotEmpty(processKey)) {
                if (ArrayUtils.contains(processKey, loopProcessKey)) {
                    logger.debug("hit module of {}", (Object[]) processKey);
                    deploySingleProcess(resourceLoader, loopProcessKey, exportDir);
                } else {
                    logger.debug("module: {} not equals process key: {}, ignore and continue find next.", loopProcessKey, processKey);
                }
            } else {
                // 所有流程
                deploySingleProcess(resourceLoader, loopProcessKey, exportDir);
            }
        }
    }

    /**
     * 部署单个流程定义
     *
     * @param resourceLoader {@link org.springframework.core.io.ResourceLoader}
     * @param processKey     模块名称
     * @throws java.io.IOException 找不到zip文件时
     */
    private void deploySingleProcess(ResourceLoader resourceLoader, String processKey, String exportDir) throws IOException {
        String classpathResourceUrl = "classpath:/deployments/" + processKey + ".bar";
        logger.debug("read workflow from: {}", classpathResourceUrl);
        Resource resource = resourceLoader.getResource(classpathResourceUrl);
        InputStream inputStream = resource.getInputStream();
        if (inputStream == null) {
            logger.warn("ignore deploy workflow module: {}", classpathResourceUrl);
        } else {
            logger.debug("finded workflow module: {}, deploy it!", classpathResourceUrl);
            ZipInputStream zis = new ZipInputStream(inputStream);
            Deployment deployment = repositoryService.createDeployment().addZipInputStream(zis).deploy();

            // export diagram
            List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).list();
            for (ProcessDefinition processDefinition : list) {
                WorkflowUtils.exportDiagramToFile(repositoryService, processDefinition, exportDir);
            }
        }
    }

    /**
     * 重新部署单个流程定义
     *
     * @param processKey 流程定义KEY
     * @throws Exception
     * @see #deployFromClasspath
     */
    public void redeploy(String exportDir, String... processKey) throws Exception {
        this.deployFromClasspath(exportDir, processKey);
    }

    /**
     * 重新部署所有流程定义
     *
     * @throws Exception
     * @see #deployFromClasspath
     */
    public void deployAllFromClasspath(String exportDir) throws Exception {
        this.deployFromClasspath(exportDir);
    }

}
