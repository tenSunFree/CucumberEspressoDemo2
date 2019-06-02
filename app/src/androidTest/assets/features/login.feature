Feature: Login
  輸入E-amil與密碼, 進行登入

  @login-feature
  Scenario Outline: 判斷輸入的格式是否正確
        # Given: 用例開始執行前的一個前置條件, 類似與編寫程式碼setup中的一些步驟
    Given On login screen
        # When: 用例開始執行的一些關鍵操作步驟, 類似點選元素等
    When Input email <email>
        # And: 一個步驟中如果存在多個Given操作，後面的Given可以用And替代
    And Input password "<password>"
    And Press submit frame layout
        # Then: 觀察結果, 就是平時用例中的驗證步驟
    Then Show error <view>

    Examples:
      | email           | password | view     |
      | test            | 5201314  | email    |
      | earth@gmail.com | 012      | password |

  @login-feature
  Scenario Outline: 判斷輸入的內容是否正確
    Given On login screen
    When Input email <email>
    And Input password "<password>"
    And Press submit frame layout
    Then Show message <isSuccess>

    Examples:
      | email                 | password | isSuccess |
      | love@gmail.com        | 5201314  | false     |
      | nba@gmail.com         | 5201314  | false     |
      | a0985092384@gmail.com | 5201314  | true      |