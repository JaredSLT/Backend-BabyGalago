package tech.tresearchgroup.babygalago.controller.scanners;

import tech.tresearchgroup.schemas.fpcalc.FPCalcOutput;
import tech.tresearchgroup.schemas.galago.enums.AudioCodecEnum;
import tech.tresearchgroup.wrappers.fpcalc.FPCalc;
import tech.tresearchgroup.wrappers.fpcalc.model.FPCalcOptions;

import java.nio.file.Path;

class AudioScanController {
    public static boolean isAudioFile(String fileExtension) {
        for (AudioCodecEnum codec : AudioCodecEnum.values()) {
            if (codec.name().equals(fileExtension)) {
                return true;
            }
        }
        return false;
    }

    public static FPCalcOutput getFPCalc(Path path) {
        FPCalc fpCalc = new FPCalc();
        FPCalcOptions fpCalcOptions = new FPCalcOptions();
        fpCalcOptions.setJson(true);
        fpCalc.setFile(path.toAbsolutePath().toString());
        fpCalc.setFpCalcOptions(fpCalcOptions);
        return fpCalc.getOutput(fpCalc.getOptions());
    }
}
