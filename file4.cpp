//---------------------------------------------------------------------------

#include <vcl.h>
#pragma hdrstop
#include <math.h>
#include "Unit1.h"
//---------------------------------------------------------------------------
#pragma package(smart_init)
#pragma link "CSPIN"
#pragma resource "*.dfm"
TForm1 *Form1;
//---------------------------------------------------------------------------
__fastcall TForm1::TForm1(TComponent* Owner)
    : TForm(Owner)
{
}
//---------------------------------------------------------------------------

int n = 1, m = 1;

void __fastcall TForm1::nSEditChange(TObject *Sender)
{
if (nSEdit->Text != "" && mSEdit->Text != "") {
    n = nSEdit->Value;
    m = mSEdit->Value;
    Form1->Width = 153 + 25 * m;
    Form1->Height = (25 * n + 53 < 218) ? 218 : 25 * n + 53;
    Matrix->ColCount = m;
    Matrix->RowCount = n;
    Matrix->Width = 25 * m + 3;
    Matrix->Height = 25 * n + 3;
}
}
//---------------------------------------------------------------------------

void __fastcall TForm1::RandomBtnClick(TObject *Sender)
{
for (int i = 0; i < n; i++)
    for (int j = 0; j < m; j++)
        Matrix->Cells[j][i] = FloatToStr(rand() % 1000 / 10.0);    
}
//---------------------------------------------------------------------------

void __fastcall TForm1::Task1RBClick(TObject *Sender)
{
SolveBtn->Enabled = true;
}
//---------------------------------------------------------------------------

void __fastcall TForm1::Task2RBClick(TObject *Sender)
{
SolveBtn->Enabled = true;
}
//---------------------------------------------------------------------------

void __fastcall TForm1::SolveBtnClick(TObject *Sender)
{
double **mas = new double*[n];
for (int i = 0; i < n; i++)
    mas[i] = new double[m];
for (int i = 0; i < n; i++)
    for (int j = 0; j < m; j++)
        mas[i][j] = StrToFloat(Matrix->Cells[j][i]);
if (Task1RB->Checked) {
    const double MAX = 1e+27;
    double min = MAX;
    for (int i = 0; i < n; i++) {
        int b;
        if (i < n / 2) {
            b = n / 2 + i;
        } else {
            b = n / 2 + n - 1 - i;
        }
        for (int j = b; j < n; j++) {
            //mas[i][j] = 0;
            // double to int type cast
            //*
            int t = mas[i][j];
            bool b = (t > 9 || t < -9) && abs(t/10%10-t%10) == 2;
            if (mas[i][j] < min && b)
                min = mas[i][j];
            //*/
        }
    }
    if (min == MAX) {
        ShowMessage("������ �������� ���");
    } else {
        ShowMessage(FloatToStr(min));
    }
} else if (Task2RB->Checked) {
    // TODO 2nd task
    const double MIN = -1e+27;
    double max = MIN;
    for (int i = 0; i < n; i++)
        for (int j = 0; j < m; j++) {
            // mas[i][j]
            bool lmin = true;
            if (i > 0)
                lmin = lmin && (mas[i][j] < mas[i - 1][j]);
            if (i < n - 1)
                lmin = lmin && (mas[i][j] < mas[i + 1][j]);
            if (j > 0)
                lmin = lmin && (mas[i][j] < mas[i][j - 1]);
            if (j < m - 1)
                lmin = lmin && (mas[i][j] < mas[i][j + 1]);
            if (lmin && (mas[i][j] > max))
                max = mas[i][j];
        }
    if (max == MIN) {
        ShowMessage("������ �������� ���");
    } else {
        ShowMessage(FloatToStr(max));
    }
}
/*
for (int i = 0; i < m; i++)
    for (int j = 0; j < n; j++)
        Matrix->Cells[i][j] = FloatToStr(mas[j][i]);
//*/
for (int i = 0; i < n; i++)
    delete [] mas[i];
delete [] mas;
}
//---------------------------------------------------------------------------

