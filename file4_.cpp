__fastcall TForm1::TForm1(TComponent* Owner)
    : TForm(Owner) {
}
void __fastcall TForm1::nSEditChange(TObject *Sender) {
    if (nSEdit->Text != "" && mSEdit->Text != "") {
        /* I/O Block */ n
        /* I/O Block */ m
        Form1->Width = 153 + 25 * m
        Form1->Height = (25 * n + 53 < 218) ? 218 : 25 * n + 53
        Matrix->ColCount = m
        Matrix->RowCount = n
        Matrix->Width = 25 * m + 3
        Matrix->Height = 25 * n + 3
    }
}
void __fastcall TForm1::RandomBtnClick(TObject *Sender) {
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            /* I/O Block */ rand() % 1000 / 10.0
        }
    }
}
void __fastcall TForm1::Task1RBClick(TObject *Sender) {
    SolveBtn->Enabled = true
}
void __fastcall TForm1::Task2RBClick(TObject *Sender) {
    SolveBtn->Enabled = true
}
void __fastcall TForm1::SolveBtnClick(TObject *Sender) {
    *mas = new [n]
    for (int i = 0; i < n; i++) {
        mas[i] = new [m]
    }
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            /* I/O Block */ mas[i][j]
        }
    }
    if (Task1RB->Checked) {
        MAX = 1e+27
        min = MAX
        for (int i = 0; i < n; i++) {
            b
            if (i < n / 2) {
                b = n / 2 + i
            } else {
                b = n / 2 + n - 1 - i
            }
            for (int j = b; j < n; j++) {
                t = mas[i][j]
                b = (t > 9 || t < -9) && abs(t/10%10-t%10) == 2
                if (mas[i][j] < min && b) {
                    min = mas[i][j]
                }
            }
        }
        if (min == MAX) {
            /* I/O Block */ "Такого элемента нет"
        } else {
            /* I/O Block */ min
        }
    } else {
        if (Task2RB->Checked) {
            MIN = -1e+27
            max = MIN
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    lmin = true
                    if (i > 0) {
                        lmin = lmin && (mas[i][j] < mas[i - 1][j])
                    }
                    if (i < n - 1) {
                        lmin = lmin && (mas[i][j] < mas[i + 1][j])
                    }
                    if (j > 0) {
                        lmin = lmin && (mas[i][j] < mas[i][j - 1])
                    }
                    if (j < m - 1) {
                        lmin = lmin && (mas[i][j] < mas[i][j + 1])
                    }
                    if (lmin && (mas[i][j] > max)) {
                        max = mas[i][j]
                    }
                }
            }
            if (max == MIN) {
                /* I/O Block */ "Такого элемента нет"
            } else {
                /* I/O Block */ max
            }
        }
    }
    for (int i = 0; i < n; i++) {
        delete [] mas[i]
    }
    delete [] mas
}
