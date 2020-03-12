#include "uimain.h"

#include <QApplication>

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    UIMain w;
    w.show();
    return a.exec();
}
