import yaml
import os
import pathlib
rootdir = pathlib.Path().resolve()
for subdir, dirs, files in os.walk(rootdir):
    test = dirs
    break

for thing in test:
    test = os.path.join(pathlib.Path().resolve(),str(thing))
    file = os.path.join(test, "animation.yml")
    with open(file, 'r') as stream:
        data_loaded = yaml.safe_load(stream)
        data_loaded["Selection"]["Point1"]["Y"] =  data_loaded["Selection"]["Point1"]["Y"] - 64
        data_loaded["Selection"]["Point2"]["Y"] =  data_loaded["Selection"]["Point2"]["Y"] - 64
        if "TriggerBuilderData" in data_loaded:
            if "TriggerBlocks" in data_loaded["TriggerBuilderData"]:
                for number in data_loaded["TriggerBuilderData"]["TriggerBlocks"]:
                    data_loaded["TriggerBuilderData"]["TriggerBlocks"][number]["Y"] = data_loaded["TriggerBuilderData"]["TriggerBlocks"][number]["Y"] - 64
    with open(file, 'w') as yml_file:
        yml_file.write(yaml.dump(data_loaded, sort_keys=False))