package e2e;

import org.apache.maven.shared.invoker.MavenInvocationException;
import org.junit.BeforeClass;
import org.junit.Test;
import scaffolding.MvnRunner;

import java.io.File;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static scaffolding.Photocopier.copyTestProjectToTemporaryLocation;

public class SingleModuleTest {

    @BeforeClass
    public static void installPluginToLocalRepo() throws MavenInvocationException {
        MvnRunner.installReleasePluginToLocalRepo();
    }

    @Test
    public void canDoIt() throws Exception {
        File projectDir = copyTestProjectToTemporaryLocation("test-project-single-module");
        String releaseVersion = String.valueOf(System.currentTimeMillis());
        List<String> output = MvnRunner.runReleaseOn(projectDir, releaseVersion);
        assertThat(output, allOf(
//            hasItem(containsString("1.0-SNAPSHOT")),
            hasItem(containsString("Release plugin running with release version " + releaseVersion))
        ));

        MvnRunner.assertArtifactInLocalRepo("com.github.danielflower.mavenplugins.testprojects", "test-project-single-module", "1.0-SNAPSHOT");
    }

}