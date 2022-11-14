package lotto;

import camp.nextstep.edu.missionutils.Console;
import lotto.domain.Lotto;
import lotto.domain.LottoGenerator;
import lotto.domain.LottoReference;
import lotto.domain.WinningNumber;

import java.util.NoSuchElementException;

public class Application {
    public static void main(String[] args) {
        LottoGenerator lottoGenerator;
        WinningNumber winningNumber;
        int[] result = new int[7];
        int totalProfit = 0;

        System.out.println("구입금액을 입력해주세요.");
        String purchaseAmount = Console.readLine();
        for (int i = 0; i < purchaseAmount.length(); i++) {
            int check = purchaseAmount.charAt(i);

            if (check < 48 || check > 58) {
                System.out.println("[ERROR]");
                // 우선 이렇게하고 커밋
                throw new NoSuchElementException("[ERROR]");
            }
        }
        lottoGenerator = new LottoGenerator(purchaseAmount);
        int generateCount = lottoGenerator.getGenerateCount();
        Lotto[] lottoGroup = new Lotto[generateCount];
        for (int i = 0; i < generateCount; i++) {
            lottoGroup[i] = new Lotto(lottoGenerator.createLottoNumber());
        }

        System.out.println(generateCount + "개를 구매했습니다.");
        for (Lotto lotto: lottoGroup) {
            System.out.println(lotto.getNumbers());
        }

        System.out.println("당첨 번호를 입력해 주세요.");
        String winInputNumber = Console.readLine();
        System.out.println("보너스 번호를 입력해 주세요.");
        String bonusInputNumber = Console.readLine();
        
        winningNumber = new WinningNumber(winInputNumber, Integer.parseInt(bonusInputNumber));

        for (int i = 0; i < generateCount; i++) {
            int rank = lottoGroup[i].lottoChecker(winningNumber.getWinNumber(), winningNumber.getBonusNumber());
            result[rank]++;
        }

        System.out.println("당첨 통계\n---");
        for (int rank = 5; rank >= 1; rank--) {
            System.out.println(LottoReference.getRankByMatchCount(rank).getWinInformation()+ " - " + result[rank] + "개");
            if (result[rank] != 0) {
                totalProfit += LottoReference.getRankByMatchCount(rank).getPrizeMoney() * result[rank];
            }
        }
        System.out.println("총 수익률은 " + Math.round((double) totalProfit / Integer.parseInt(purchaseAmount)* 1000) / 10.0 + "%입니다.");
    }
}
