package net.awired.jstest.mojo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import net.awired.jscoverage.instrumentation.JsInstrumentedSource;
import net.awired.jscoverage.instrumentation.JsInstrumentor;
import net.awired.jstest.common.io.DirectoryCopier;
import net.awired.jstest.common.io.FileUtilsWrapper;
import net.awired.jstest.mojo.inherite.AbstractJsTestMojo;
import net.awired.jstest.resource.ResourceDirectory;
import net.awired.jstest.resource.ResourceDirectoryScanner;
import net.awired.jstest.runner.RunnerType;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import com.yahoo.platform.yuitest.coverage.DirectoryInstrumenter;


/**
 * @goal processTestSources
 * @phase process-test-sources
 */
public class ProcessTestSourcesMojo extends AbstractJsTestMojo {

    private ResourceDirectoryScanner scriptDirScanner = new ResourceDirectoryScanner();
    private JsInstrumentor jsInstrumentor = new JsInstrumentor();
    private FileUtilsWrapper fileUtilsWrapper = new FileUtilsWrapper();
    private DirectoryCopier directoryCopier = new DirectoryCopier();

    @Override
    protected void run() throws MojoExecutionException, MojoFailureException {
        if (isSkipTestsCompile()) {
            getLog().debug("Skipping processing test sources");
            return;
        }
        try {
            directoryCopier.copyDirectory(getSourceDir(), getTargetSourceDirectory());
        } catch (IOException e) {
            throw new RuntimeException("Cannot copy source directory to target", e);
        }
        if (isCoverage()) {
            getLog().info("Instrumentation of javascript sources");
            processInstrumentSources();
        }
    }

    private void processInstrumentSources() {
        if (isCoverage()) {
            ResourceDirectory sourceScriptDirectory = buildSrcResourceDirectory();
            List<String> scan = scriptDirScanner.scan(sourceScriptDirectory);
            RunnerType runnerType = buildAmdRunnerType();
            if (runnerType.equals(RunnerType.YUI)) {
        		DirectoryInstrumenter.setVerbose(false);
                //in this case fileArgs[0] and outputLocation are directories
                try {
                	getInstrumentedDirectory().mkdirs();
					DirectoryInstrumenter.instrument(getSourceDir().getAbsolutePath(), getTargetSourceDirectory().getAbsolutePath());
				} catch (FileNotFoundException e) {
					throw new IllegalStateException("cannot find source code to instrument", e);
				} catch (Exception e) {
					throw new IllegalStateException("cannot instrument source code", e);
				} 
            }
            for (String file : scan) {
                
                if (!file.toLowerCase().endsWith(".js") && !file.toLowerCase().endsWith(".json")) {
                    getLog().debug("Skip instrumentation of file " + file + " as its not a .js file");
                    continue;
                }
                if (runnerType.getAmdFile() != null && runnerType.getAmdFile().equals(file)) {
                    getLog().debug("Skip instrumentation of Amd file : " + file);
                    continue;
                }

                try {

                    String fileContent = "";
                    if (file.toLowerCase().endsWith(".json")) {
                        fileContent = fileUtilsWrapper.readFileToString(new File(sourceScriptDirectory.getDirectory(), file));
                    } else {

                    	JsInstrumentedSource instrument = jsInstrumentor.instrument(file,
                    			fileUtilsWrapper.readFileToString(new File(sourceScriptDirectory.getDirectory(), file)));
                        fileContent = instrument.getIntrumentedSource();
                    }
                	
                	File instrumentedfile = new File(getInstrumentedDirectory(), file);

                	fileUtilsWrapper.forceMkdir(instrumentedfile.getParentFile());

                	fileUtilsWrapper.writeStringToFile(instrumentedfile, fileContent, "UTF-8");

                } catch (FileNotFoundException e) {
                    throw new IllegalStateException("cannot find source code to instrument", e);
                } catch (Exception e) {
                    throw new IllegalStateException("cannot instrument source code", e);
                }
            }
        }
    }
    
}
