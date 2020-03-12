/********************************************************************************
** Form generated from reading UI file 'uimain.ui'
**
** Created by: Qt User Interface Compiler version 5.13.1
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_UIMAIN_H
#define UI_UIMAIN_H

#include <QtCore/QVariant>
#include <QtWidgets/QApplication>
#include <QtWidgets/QMainWindow>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_UIMain
{
public:
    QWidget *centralwidget;

    void setupUi(QMainWindow *UIMain)
    {
        if (UIMain->objectName().isEmpty())
            UIMain->setObjectName(QString::fromUtf8("UIMain"));
        UIMain->resize(800, 600);
        QFont font;
        font.setFamily(QString::fromUtf8("Ubuntu"));
        UIMain->setFont(font);
        centralwidget = new QWidget(UIMain);
        centralwidget->setObjectName(QString::fromUtf8("centralwidget"));
        UIMain->setCentralWidget(centralwidget);

        retranslateUi(UIMain);

        QMetaObject::connectSlotsByName(UIMain);
    } // setupUi

    void retranslateUi(QMainWindow *UIMain)
    {
        UIMain->setWindowTitle(QCoreApplication::translate("UIMain", "IntelliTraffic", nullptr));
    } // retranslateUi

};

namespace Ui {
    class UIMain: public Ui_UIMain {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_UIMAIN_H
