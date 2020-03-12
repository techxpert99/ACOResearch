#include "uimain.h"
#include "ui_uimain.h"

UIMain::UIMain(QWidget *parent)
    : QMainWindow(parent)
    , ui(new Ui::UIMain)
{
    ui->setupUi(this);
    QSize scrsz = QApplication::screens()[0]->size();
    this->resize(scrsz);

}

UIMain::~UIMain()
{
    delete ui;
}

