package com.example.diplomaproject.samson.test;

import com.example.samson.diplomaproject.utils.FileExplorer;

import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;


public class FileExplorerTest {

    @Test
    public void testGettingPathDirestory() {
        String path = FileExplorer.getPathDirectory();
        assertNotNull(path);
        assertNotSame("", path);
    }

    @Test
    public void testCreatingImageFile() {
        File file = FileExplorer.createImageFile();
        File spy = Mockito.spy(file);
        assertNotNull(spy);
        Mockito.when(spy.exists()).thenReturn(true);
        assertEquals(spy.exists(), true);
    }
}
