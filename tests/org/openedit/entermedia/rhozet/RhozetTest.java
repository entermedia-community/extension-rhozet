package org.openedit.entermedia.rhozet;

import org.openedit.entermedia.Asset;
import org.openedit.entermedia.BaseEnterMediaTest;
import org.openedit.entermedia.creator.ConvertInstructions;
import org.openedit.entermedia.creator.ConvertResult;

import com.openedit.WebPageRequest;
import com.openedit.modules.scriptrunner.ScriptModule;
import com.openedit.page.Page;

public class RhozetTest extends BaseEnterMediaTest
{

	public RhozetTest(String inArg0)
	{
		super(inArg0);
	}

	protected void setUp() throws Exception {
		super.setUp();
		String rootPath = System.getProperty( "oe.root.path" );
		if ( rootPath == null )
		{
			System.setProperty("oe.root.path", "base");
		}

	}
	public void xtestConvert() throws Exception
	{
		ConvertInstructions inst = new ConvertInstructions();
		
		String sourcepath = "test/test.doc";
		Asset asset = getMediaArchive().createAsset(sourcepath);
		asset.setProperty("fileformat", "doc");
		inst.setAssetSourcePath(asset.getSourcePath());
		inst.setOutputExtension("pdf");
		RhozetVideoCreator creator = (RhozetVideoCreator)getFixture().getModuleManager().getBean("rhozetVideoCreator");
		String tmppath = creator.populateOutputPath(getMediaArchive(), inst);
		
		Page out = getFixture().getPageManager().getPage(tmppath);
		getFixture().getPageManager().removePage(out);
		assertFalse(out.exists());
		if( !out.exists() || out.getContentItem().getLength()==0)
		{
			//Create 
			ConvertResult tmpresult = creator.convert(getMediaArchive(), asset, out, inst);
			assertTrue( tmpresult.isOk() );
			assertTrue(out.exists());
		}	
		
	}

	public void testApi() throws Exception
	{

		Page testpage  = getPage("/entermedia/catalog/components/rhozet/scripts/testscript.html");
		//assertTrue(testpage.exists());
		
		WebPageRequest req = getFixture().createPageRequest(testpage.getPath());
		getFixture().getEngine().executePathActions(req);
		//ScriptModule module = (ScriptModule) getFixture().getModuleManager().getModule("Script");
		
		
		//module.run(req);
		

	}

	

	public void testMetaDataExtraction() throws Exception
	{
	

	}

	
}
