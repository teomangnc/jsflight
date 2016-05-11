package com.focusit.jsflight.player.fileconfigholder;

import com.focusit.jsflight.player.config.ScriptEventConfiguration;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ScriptEventExectutionController extends UIFileConfigHolder
{
    private static final long serialVersionUID = 8326565501493347454L;

    private static final ScriptEventExectutionController INSTANCE = new ScriptEventExectutionController();

    public static ScriptEventExectutionController getInstance()
    {
        return INSTANCE;
    }

    private ScriptEventConfiguration configuration;

    @Override
    public void load(String file) throws Exception
    {
        ObjectInputStream stream = getInputStream(file);
        configuration.setScript((String)stream.readObject());
        configuration.setFilename((String)stream.readObject());
    }

    @Override
    public void store(String file) throws Exception
    {
        ObjectOutputStream stream = getOutputStream(file);
        stream.writeObject(configuration.getScript());
        stream.writeObject(configuration.getFilename());
    }

    @Override
    protected String getControllerDataFilename()
    {
        return "scriptEventExecution";
    }

    public void setConfiguration(ScriptEventConfiguration configuration) {
        this.configuration = configuration;
    }
}