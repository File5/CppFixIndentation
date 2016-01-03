int m = StrToInt(Edit1->Text);
bool v = CheckBox1->Checked;
switch (m) {
case 1: case 3: case 5: case 7: case 8: case 10: case 12:
    Label3->Caption = "31";
    break;
case 4: case 6: case 9: case 11:
    Label3->Caption = "30";
    break;
case 2:
    if (v) Label3->Caption = "29";
    else Label3->Caption = "28";
    break;
}