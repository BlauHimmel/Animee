package com.zhouyuming.animee;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.zhouyuming.animee.model.AnimeModel;
import com.zhouyuming.animee.model.Model;
import com.zhouyuming.animee.model.ModelArray;
import com.zhouyuming.animee.utils.FileUtils;
import com.zhouyuming.animee.utils.JsonUtils;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
	@Test
	public void useAppContext() throws Exception {
		// Context of the app under test.
		Context appContext = InstrumentationRegistry.getTargetContext();

	}
}
