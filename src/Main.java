import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Main {

    public static DefectChecker parserFromBytecode(String bytecode) throws Exception {
        if (bytecode.length() < 1)
            return null;
        bytecode = bytecode.replaceAll("a165627a7a72305820\\S{64}0029$", ""); // remove swarm hash
        // we only need runtime bytecode, remove creation bytecode
        if (bytecode.contains("f30060806040")) {
            bytecode = bytecode.substring(bytecode.indexOf("f30060806040") + 4);
        }

        BinaryAnalyzer binaryAnalyzer = new BinaryAnalyzer(bytecode);
        if (binaryAnalyzer.legalContract) {
            try {
                DefectChecker defectChecker = new DefectChecker(binaryAnalyzer);
                defectChecker.detectAllSmells();
                return defectChecker;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        long startTime = System.currentTimeMillis();
        /***** Detect From Bytecode *****/
        String bytecode = "608060405234801561001057600080fd5b50600080546001600160a01b03191633178155308152600160205260409020606490556102a8806100426000396000f3fe608060405234801561001057600080fd5b50600436106100415760003560e01c80638da5cb5b14610046578063cdda418c14610076578063f30dc5a81461008c575b600080fd5b600054610059906001600160a01b031681565b6040516001600160a01b0390911681526020015b60405180910390f35b61007e6100ac565b60405190815260200161006d565b61007e61009a3660046101c7565b60016020526000908152604090205481565b6040516bffffffffffffffffffffffff193360601b1660208201526000908190439042906034016040516020818303038152906040528051906020012060001c6100f6919061020d565b6040516bffffffffffffffffffffffff194160601b166020820152459042906034016040516020818303038152906040528051906020012060001c61013b919061020d565b610145444261022f565b61014f919061022f565b610159919061022f565b610163919061022f565b61016d919061022f565b60405160200161017f91815260200190565b60408051601f19818403018152919052805160209091012090506101a864e8d4a510008261020d565b6101b79064e8d4a51000610248565b6101c1908261025f565b91505090565b6000602082840312156101d957600080fd5b81356001600160a01b03811681146101f057600080fd5b9392505050565b634e487b7160e01b600052601160045260246000fd5b60008261022a57634e487b7160e01b600052601260045260246000fd5b500490565b80820180821115610242576102426101f7565b92915050565b8082028115828204841417610242576102426101f7565b81810381811115610242576102426101f756fea264697066735822122003b63ff42f7b92c745b49dee1cb65e1977fddffcb1556b69e0ca8c18cc8f79e264736f6c63430008110033";
        DefectChecker byteDefectChecker = parserFromBytecode(bytecode);
        System.out.println(byteDefectChecker.printAllDetectResult());

        long endTime = System.currentTimeMillis();
        System.out.println("Running time：" + (endTime - startTime) + "ms");
    }
}
